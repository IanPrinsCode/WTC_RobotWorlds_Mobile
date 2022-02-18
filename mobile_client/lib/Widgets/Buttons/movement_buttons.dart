import 'package:flutter/material.dart';
import 'package:fluttericon/font_awesome5_icons.dart';
import 'package:mobile_client/Services/api_notifiers.dart';
import 'package:provider/provider.dart';

class MoveButton extends StatelessWidget {
  const MoveButton({Key? key, required this.direction, required this.context})
      : super(key: key);
  final String direction;
  final BuildContext context;

  @override
  Widget build(BuildContext context) {
    final notifier = Provider.of<ApiNotifiers>(context);
    return ElevatedButton(
        onPressed: () async {
          await _doMovement(notifier, direction);
          showMessage(notifier, context);
        },
        child: Icon(_changeIcon(direction), color: Colors.cyanAccent));
  }
}

_doMovement(ApiNotifiers notifier, String direction) async {
  // await notifier.doMove(direction).then((value) => notifier.updateRobots());
  switch (direction) {
    case 'forward':
      await notifier.doForward().then((value) => notifier.updateRobots());
      break;
    case 'back':
      await notifier.doBack().then((value) => notifier.updateRobots());
      break;
    default:
      break;
  }
}

_changeIcon(String direction) {
  switch (direction) {
    case 'forward':
      return FontAwesome5.angle_double_up;
    case 'back':
    case 'backward':
      return FontAwesome5.angle_double_down;
    default:
      return FontAwesome5.angle_double_up;
  }
}

void showMessage(ApiNotifiers notifier, BuildContext context) {
  final moveMessage = SnackBar(
    duration: const Duration(seconds: 1),
    content: Text(notifier.result.requestMessage),
    backgroundColor: Colors.blueGrey,
  );

  if (notifier.result.requestMessage != '' &&
      notifier.result.requestMessage != 'DONE' &&
      notifier.result.requestMessage != 'done') {
    ScaffoldMessenger.of(context).showSnackBar(moveMessage);
  }
}
