import 'package:flutter/cupertino.dart';
import 'package:mobile_client/Models/obstacle.dart';
import 'package:mobile_client/Models/result.dart';
import 'package:mobile_client/Models/robot.dart';
import 'package:mobile_client/Models/world.dart';

import 'api_requests.dart';

class ApiNotifiers extends ChangeNotifier {
  late final String ip;
  late final String port;
  late final Http httpService = Http();

  World currentWorld = World(size: 2, name: '', map: {});
  Map<List<int>, String> worldMap = {};
  List<Obstacle> obstacles = [];
  List<Robot> robots = [];
  List<World> worlds = [];
  late String myRobot;

  late Result result = Result(requestResult: '', requestMessage: '');

  Future<void> updateRobots() async {
    //get data from api
    robots = await httpService.getRobots();
    // update UI with latest data
    notifyListeners();
  }

  Future<void> updateWorlds() async {
    worlds = await httpService.getWorlds();
    notifyListeners();
  }

  Future<void> removeRobot(String name) async {
    await Http().deleteRobot(name);
    updateRobots();
  }

  Future<void> loadWorld(String name) async {
    await Http().loadWorld(name);
  }

  Future<void> saveWorld(String name) async {
    await Http().saveWorld(name);
  }

  Future<void> updateObstacles() async {
    obstacles = await Http().getObstacles();
    notifyListeners();
  }

  Future<void> removeObstacles(List<Obstacle> obstacles) async {
    await Http().removeObstacles(obstacles);
    updateObstacles();
  }

  Future<void> addRobot(String name, String robotType) async {
    result = await Http().launchRobot(name, robotType);
    myRobot = name;
    updateRobots();
  }

  Future<void> addObstacles(List<Obstacle> obstacles) async {
    await Http().addObstacles(obstacles);
    updateObstacles();
  }

  Future<void> getCurrentWorld() async {
    currentWorld = await Http().getCurrentWorld();
    notifyListeners();
  }

  Future<void> doForward() async {
    result = await Http().robotMove(myRobot, 'forward');
    getCurrentWorld();
  }

  Future<void> doBack() async {
    result = await Http().robotMove(myRobot, 'back');
    getCurrentWorld();
  }

  Future<void> doRight() async {
    await Http().robotTurn(myRobot, 'right');
    getCurrentWorld();
  }

  Future<void> doLeft() async {
    await Http().robotTurn(myRobot, 'left');
    getCurrentWorld();
  }

  Future<void> doLook() async {
    result = await Http().robotActions(myRobot, "look");
    getCurrentWorld();
  }

  Future<void> doFire() async {
    result = await Http().robotActions(myRobot, "fire");
    getCurrentWorld();
  }

  Future<void> doReload() async {
    result = await Http().robotActions(myRobot, "reload");
    getCurrentWorld();
  }

  Future<void> doMine() async {
    result = await Http().robotActions(myRobot, "mine");
    getCurrentWorld();
  }

  Future<void> doRepair() async {
    result = await Http().robotActions(myRobot, "repair");
    getCurrentWorld();
  }
}
