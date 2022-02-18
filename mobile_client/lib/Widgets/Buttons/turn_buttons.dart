import 'package:flutter/material.dart';
import 'package:fluttericon/font_awesome5_icons.dart';
import 'package:mobile_client/Services/api_notifiers.dart';
import 'package:provider/provider.dart';

class TurnButton extends StatelessWidget {
  const TurnButton({Key? key, required this.direction}) : super(key: key);
  final String direction;

  @override
  Widget build(BuildContext context) {
    final notifier = Provider.of<ApiNotifiers>(context);
    return ElevatedButton(
        onPressed: () async {
          await _turn(notifier, direction);
        },
        child: Icon(_changeIcon(direction), color: Colors.cyanAccent));
  }
}

_turn(ApiNotifiers notifier, String direction) async {
  // await notifier.doMove(direction).then((value) => notifier.updateRobots());
  switch (direction) {
    case 'right':
      await notifier.doRight().then((value) => notifier.updateRobots());
      break;
    case 'left':
      await notifier.doLeft().then((value) => notifier.updateRobots());
      break;
    default:
      break;
  }
}

_changeIcon(String direction) {
  switch (direction) {
    case 'right':
      return FontAwesome5.angle_double_right;
    case 'left':
      return FontAwesome5.angle_double_left;
    default:
      return FontAwesome5.angle_double_right;
  }
}
