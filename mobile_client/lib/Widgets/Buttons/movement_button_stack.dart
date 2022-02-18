import 'package:flutter/material.dart';

import 'movement_buttons.dart';
import 'turn_buttons.dart';

class MovementButtonStack extends StatelessWidget {
  const MovementButtonStack({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: [
        Align(
          alignment: Alignment.topCenter,
          child: MoveButton(
            direction: 'forward',
            context: context,
          ),
        ),
        Align(
          alignment: Alignment.bottomCenter,
          child: MoveButton(
            direction: 'back',
            context: context,
          ),
        ),
        const Align(
          alignment: Alignment.centerRight,
          child: TurnButton(
            direction: 'right',
          ),
        ),
        const Align(
          alignment: Alignment.centerLeft,
          child: TurnButton(
            direction: 'left',
          ),
        ),
      ],
    );
  }
}
