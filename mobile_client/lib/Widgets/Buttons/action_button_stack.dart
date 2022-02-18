import 'package:flutter/material.dart';

import 'action_buttons.dart';

class ActionButtonStack extends StatelessWidget {
  const ActionButtonStack({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: [
        Align(
          alignment: Alignment.topRight,
          child: ActionButton(
            direction: 'reload',
            context: context,
          ),
        ),
        Align(
          alignment: Alignment.topLeft,
          child: ActionButton(
            direction: 'look',
            context: context,
          ),
        ),
        Align(
          alignment: Alignment.bottomLeft,
          child: ActionButton(
            direction: 'mine',
            context: context,
          ),
        ),
        Align(
          alignment: Alignment.bottomRight,
          child: ActionButton(
            direction: 'repair',
            context: context,
          ),
        ),
        Align(
          alignment: Alignment.center,
          child: ActionButton(
            direction: 'fire',
            context: context,
          ),
        ),
      ],
    );
  }
}
