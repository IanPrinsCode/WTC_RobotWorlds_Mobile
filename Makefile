ifeq ($(MVN),)
    MVN  := mvn
endif

ifeq ($(GIT),)
    GIT  := git
endif

ifeq ($(REF_VERSION),)
   REF_VERSION  := 0.2.0
endif

ifeq ($(IGNORE_TESTS),)
   IGNORE_TESTS  := -DskipTests
endif

ifeq ($(DEFAULT),)
   DEFAULT  := -Dtest=RobotLaunchTests#default_*
endif

ifeq ($(IGNORE_TESTS),)
   IGNORE_TESTS  := -DskipTests
endif

ifeq ($(RELEASE_PATH),)
    RELEASE_PATH := -Ddir=../.libs
endif

ifeq ($(RELEASE_VERSION),)
    RELEASE_VERSION  := $(shell xmllint --xpath "/*[local-name() = 'project']/*[local-name() = 'version']/text()" server/pom.xml | perl -pe 's/-SNAPSHOT//')
endif

ifeq ($(VERSION),)
    VERSION  := $(shell xmllint --xpath "/*[local-name() = 'project']/*[local-name() = 'version']/text()" server/pom.xml)
endif

ifeq ($(NEXT_VERSION),)
    NEXT_VERSION  := $(shell echo $(RELEASE_VERSION) | perl -pe 's{^(([0-9]\.)+)?([0-9]+)$$}{$$1 . ($$3 + 1)}e')
endif

ifneq (,$(findstring -SNAPSHOT,$(RELEASE_VERSION)))
   RELEASE_VERSION_NSNP = $(shell echo $(RELEASE_VERSION) | perl -pe 's/-SNAPSHOT//')
else
   RELEASE_VERSION_NSNP = $(RELEASE_VERSION)
endif

ifeq (,$(findstring -SNAPSHOT,$(NEXT_VERSION)))
   NEXT_VERSION_SNP = $(NEXT_VERSION)-SNAPSHOT
else
   NEXT_VERSION_SNP = $(NEXT_VERSION)
endif

######################## BUILD TARGETS ###########################

.PHONY: all package compile check test doc docs javadoc clean help
.ONESHELL:

all: | clean init compile package test

build_server: | clean init compile

clean:
	rm -rf .libs/
	rm -rf /server/target/
	@ $(MVN) clean

compile:
	@ $(MVN) compile

init:
	@ $(MVN) initialize

init_flutter:
	cd mobile_client
	flutter clean
	flutter pub upgrade
	flutter pub get


build_client:
	cd mobile_client
	flutter build apk

flutter_test:
	cd mobile_client
	flutter test

# specific test name. so only acceptance tests run
test:
	make run_server ; (fuser -k 5000/tcp) ; sleep 5 ; /
	make run_obs_server ; (fuser -k 5000/tcp) ; sleep 5 ; /
	make run_2x2_server ; (fuser -k 5000/tcp)

verify:
	@ $(MVN) verify $(IGNORE_TESTS)

build_docker:
	docker build -t robot-worlds-server:1.0.0 .

push_docker:
	make build_docker
	docker push gitlab.wethinkco.de:5050/iprins/uss-voyager

run_default_docker:
	docker run --name test-docker -p 5000:5050 -p 5001:5051 robot-worlds-server:1.0.0 -p 5050 -h 5051 &
	$(MVN) -Dtest=*#default* test
	docker stop test-docker
	docker rm test-docker

run_2x2_docker:
	docker run --name test-docker -p 5000:5050 -p 5001:5051 robot-worlds-server:1.0.0 -p 5050 -h 5051 -s 2 &
	$(MVN) -Dtest=*#two* test
	docker stop test-docker
	docker rm test-docker

run_oneObs_docker:
	docker run --name test-docker -p 5000:5050 -p 5001:5051 robot-worlds-server:1.0.0 -p 5050 -h 5051 -s 2 -o 0,1 &
	$(MVN) -Dtest=*#oneObs* test
	docker stop test-docker
	docker rm test-docker

demo_docker:
	docker run --name test-docker -p 5000:5050 -p 5001:5051 robot-worlds-server:1.0.0 -p 5050 -h 5051 -s 5 -o 0,1

run_server:
	@ (java -jar .libs/server-1.2.0-jar-with-dependencies.jar -z &) ; $(MVN) -Dtest=*#default* test

run_obs_server:
	@ (java -jar .libs/server-1.2.0-jar-with-dependencies.jar -z -s 2 -o 0,1 &) ; $(MVN) -Dtest=*#oneObs* test

run_2x2_server:
	@ (java -jar .libs/server-1.2.0-jar-with-dependencies.jar -z -s 2 &) ; $(MVN) -Dtest=*#two* test

run_reference_server:
	@ (java -jar .libs/reference-server-$(REF_VERSION).jar &) ; $(MVN) test ; kill $$(lsof -ti:5000)

test_docker:
	make run_default_docker
	sleep 5
	make run_2x2_docker
	sleep 5
	make run_oneObs_docker

# makes package even if test fail
package:
	@ ($(MVN) $(IGNORE_TESTS) package)

release:
	@ ($(MVN) $(IGNORE_TESTS) package $(RELEASE_PATH))

tag:
	@ $(GIT) tag -a release-$(RELEASE_VERSION) -m "release build" && $(GIT) push --tags

version_bump:
	@ echo setting next version: $(NEXT_VERSION_SNP)
	@ $(MVN) versions:set -DgenerateBackupPoms=false -DnewVersion=$(NEXT_VERSION_SNP)

version_release:
	@ echo setting release version: $(RELEASE_VERSION_NSNP)
	@ $(MVN) versions:set -DgenerateBackupPoms=false -DnewVersion=$(RELEASE_VERSION_NSNP)

update: | version_release tag version_bump

help:
	@ echo "Usage   :  make <target>"
	@ echo "Targets :"
	@ echo "   all ........... Builds the project"
	@ echo "   clean ......... Removes build products"
	@ echo "   compile ....... Compiles all Java files"
	@ echo "   test .......... Builds and runs all unit tests"
	@ echo "   install .......... Builds and installs to local repository"
	@ echo "   help .......... Prints this help message"