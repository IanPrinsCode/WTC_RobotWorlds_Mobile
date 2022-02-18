import 'package:flutter/material.dart';
import 'package:fluttericon/font_awesome5_icons.dart';
import 'package:mobile_client/Services/api_notifiers.dart';
import 'package:provider/provider.dart';

class ActionButton extends StatelessWidget {
  const ActionButton({Key? key, required this.direction, required this.context})
      : super(key: key);
  final String direction;
  final BuildContext context;

  @override
  Widget build(BuildContext context) {
    final notifier = Provider.of<ApiNotifiers>(context);
    return ElevatedButton(
        onPressed: () async {
          await _doAction(notifier, direction);
          showMessage(notifier, context);
        },
        child: Icon(_changeIcon(direction), color: Colors.cyanAccent));
  }
}

_doAction(ApiNotifiers notifier, String action) async {
  // await notifier.doMove(direction).then((value) => notifier.updateRobots());
  switch (action) {
    case 'reload':
      await notifier.doReload().then((value) => notifier.updateRobots());
      break;
    case 'look':
      await notifier.doLook().then((value) => notifier.updateRobots());
      break;
    case 'mine':
      await notifier.doMine().then((value) => notifier.updateRobots());
      break;
    case 'repair':
      await notifier.doRepair().then((value) => notifier.updateRobots());
      break;
    case 'fire':
      await notifier.doFire().then((value) => notifier.updateRobots());
      break;
    default:
      break;
  }
}

_changeIcon(String action) {
  switch (action) {
    case 'reload':
      return FontAwesome5.sync_icon;
    case 'look':
      return FontAwesome5.binoculars;
    case 'mine':
      return FontAwesome5.bomb;
    case 'repair':
      return FontAwesome5.tools;
    case 'fire':
      return FontAwesome5.crosshairs;
    default:
      return FontAwesome5.question_circle;
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
