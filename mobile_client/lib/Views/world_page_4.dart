import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:mobile_client/Services/api_notifiers.dart';
import 'package:mobile_client/Widgets/Canvas/robot_buttons.dart';
import 'package:mobile_client/Widgets/Canvas/world_grid.dart';
import 'package:provider/provider.dart';

class WorldPage extends StatefulWidget {
  final String title;

  const WorldPage({Key? key, required this.title}) : super(key: key);

  @override
  State<WorldPage> createState() => _WorldPage();
}

class _WorldPage extends State<WorldPage> {
  @override
  void initState() {
    super.initState();
    Provider.of<ApiNotifiers>(context, listen: false).getCurrentWorld();
  }

  @override
  Widget build(BuildContext context) {
    final notifier = Provider.of<ApiNotifiers>(context);
    var shields;
    var shots;
    for (var robot in notifier.robots) {
      if (robot.name == notifier.myRobot) {
        shields = robot.state['shield'];
        shots = robot.state['shots'];
      }
    }
    if (shields == null) {
      shields = 'N/A';
      shots = 'N/A';
    }

    return Scaffold(
      backgroundColor: Colors.teal[100],
      body: Padding(
        padding: const EdgeInsetsDirectional.only(top: 50),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            Center(
              child: Padding(
                padding:
                    const EdgeInsets.symmetric(vertical: 10.0, horizontal: 20.0),
                child: Expanded(
                  child: WorldMap(notifier.currentWorld),
                ),
              ),
            ),
            Center(child: Text("Shields: $shields")),
            Padding(
                padding: const EdgeInsetsDirectional.only(bottom: 10.0),
                child: Text("Shots: $shots")),
            const RobotButtons(),
            Align(
              alignment: Alignment.bottomCenter,
              child: Padding(
                padding: const EdgeInsets.all(50),
                child: ElevatedButton(
                    onPressed: () {
                      Navigator.of(context).pop();
                    },
                    style: ElevatedButton.styleFrom(
                      primary: Colors.redAccent, // set the background color
                    ),
                    child: const Text("EXIT")),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
