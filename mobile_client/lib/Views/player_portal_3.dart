import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:mobile_client/Services/api_notifiers.dart';
import 'package:mobile_client/Views/world_page_4.dart';
import 'package:provider/provider.dart';

class PlayerPage extends StatefulWidget {
  final String title;

  const PlayerPage({Key? key, required this.title}) : super(key: key);

  @override
  _PlayerPageState createState() => _PlayerPageState();
}

class _PlayerPageState extends State<PlayerPage> {
  String name = "";
  String robotType = "robot";
  List<String> robots = ["fighter", "sniper", "standard", "robot", "tank"];

  @override
  Widget build(BuildContext context) {
    final notifier = Provider.of<ApiNotifiers>(context);

    return Scaffold(
      backgroundColor: Colors.teal[100],
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Container(
        padding: const EdgeInsets.all(10),
          child: Column(
            children: [
              TextField(onChanged: (text){
                setState(() {
                  name = text;
                });
              },
              decoration: const InputDecoration(
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.all(Radius.circular(10)),
                ),
                label: Text("Name of Robot"),
                ),
              ),
              const SizedBox(
                height: 10,
              ),
              DropdownButtonFormField(
                value: robotType,
                items: robots.map((robot) {
                  return DropdownMenuItem(
                    child: Text(robot),
                    value: robot,
                    );
                  }).toList(),
                onChanged: (String? newVal) {
                  robotType = newVal!;
                },
                decoration: const InputDecoration(
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.all(Radius.circular(10)),
                  ),
                ),
                style: const TextStyle(
                    color: Colors.blue
                ),
              ),
              const SizedBox(
                height: 10,
              ),
              Align(
                alignment: Alignment.center,
                child: ElevatedButton(
                  onPressed: () async {
                    await notifier.addRobot(name, robotType);
                    final launchMessage = SnackBar(duration: const Duration(seconds: 1), content: Text(notifier.result.requestMessage),
                      backgroundColor: Colors.blueGrey,);

                    if (notifier.result.requestResult == 'OK') {
                      Navigator.push(context,
                        MaterialPageRoute(
                            builder: (context) => const WorldPage(title: "World page")),
                      );
                    }

                    ScaffoldMessenger.of(context).showSnackBar(launchMessage);
                  },
                  child: const Text("Launch"),
                ),
              )
            ],
        ),
      ),
    );
  }
}
