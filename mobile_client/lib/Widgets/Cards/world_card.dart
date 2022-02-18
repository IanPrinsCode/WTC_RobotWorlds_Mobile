import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:fluttericon/rpg_awesome_icons.dart';
import 'package:mobile_client/Services/api_notifiers.dart';
import 'package:provider/provider.dart';

class Block extends StatefulWidget {
  const Block({Key? key, required this.object, required this.cell})
      : super(key: key);
  final String object;
  final List<int> cell;

  @override
  State<Block> createState() => _BlockState();
}

class _BlockState extends State<Block> {
  @override
  Widget build(BuildContext context) {
    final notifier = Provider.of<ApiNotifiers>(context);
    var objectIcon;

    if (widget.object == "O") {
      objectIcon = const Icon(RpgAwesome.tower, color: Colors.blueGrey);
    } else if (widget.object == "M") {
      objectIcon = const Icon(
        RpgAwesome.skull,
        color: Colors.black,
      );
    } else if (widget.object == "R") {
      for (var robot in notifier.robots) {
        if (robot.state["position"][0] == widget.cell[0] &&
            robot.state["position"][1] == widget.cell[1]) {
          objectIcon = _rotateRobotIcon(
              robot.state["direction"], robot.name == notifier.myRobot);
        }
      }
    } else if (widget.object == "#") {
      objectIcon = const Icon(
        RpgAwesome.pine_tree,
        color: Colors.green,
      );
    }
    return Card(
      color: Colors.white,
      child: Center(child: objectIcon),
    );
  }
}

RotatedBox _rotateRobotIcon(String direction, bool isMyRobot) {
  var color = isMyRobot ? Colors.green : Colors.red;
  if (direction == "WEST") {
    return RotatedBox(
      quarterTurns: 3,
      child: Icon(Icons.android_sharp, color: color),
    );
  } else if (direction == "EAST") {
    return RotatedBox(
      quarterTurns: 1,
      child: Icon(Icons.android_sharp, color: color),
    );
  } else if (direction == "SOUTH") {
    return RotatedBox(
      quarterTurns: 2,
      child: Icon(Icons.android_sharp, color: color),
    );
  } else {
    return RotatedBox(
      quarterTurns: 0,
      child: Icon(Icons.android_sharp, color: color),
    );
  }
}
