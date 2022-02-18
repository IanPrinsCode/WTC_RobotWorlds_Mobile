import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:mobile_client/Widgets/Buttons/action_button_stack.dart';
import 'package:mobile_client/Widgets/Buttons/movement_button_stack.dart';

class RobotButtons extends StatelessWidget {
  const RobotButtons({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceAround,
      crossAxisAlignment: CrossAxisAlignment.center,
      children: const [
        SizedBox(
          width: 190,
          height: 125,
          child: MovementButtonStack(),
        ),
        SizedBox(
          width: 190,
          height: 125,
          child: ActionButtonStack(),
        ),
      ],
    );
  }
}
