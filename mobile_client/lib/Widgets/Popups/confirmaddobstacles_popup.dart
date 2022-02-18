import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:mobile_client/Models/obstacle.dart';
import 'package:mobile_client/Services/api_notifiers.dart';
import 'package:provider/provider.dart';

class ConfirmAdd extends StatelessWidget {
  const ConfirmAdd(BuildContext context, {Key? key, required this.obstacles})
      : super(key: key);

  final List<Obstacle> obstacles;

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: Center(
          child: Text(
              "Are you sure you want to add ${obstacles.length} obstacles?")),
      content: Column(
        mainAxisSize: MainAxisSize.min,
        crossAxisAlignment: CrossAxisAlignment.start,
      ),
      actions: <Widget>[
        ElevatedButton(
          onPressed: () async {
            Navigator.of(context).pop();
          },
          child: const Text('Cancel'),
        ),
        ElevatedButton(
          onPressed: () async {
            Provider.of<ApiNotifiers>(context, listen: false)
                .addObstacles(obstacles);
            Navigator.of(context).pop();
          },
          child: const Text('Continue'),
        ),
      ],
    );
  }
}
