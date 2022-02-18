import 'package:flutter/material.dart';
import 'package:mobile_client/Services/api_notifiers.dart';
import 'package:provider/provider.dart';

import '../Popups/confirmdeleteobstacles_popup.dart';

class BottomSheetContentObstacles extends StatefulWidget {
  const BottomSheetContentObstacles({Key? key}) : super(key: key);

  @override
  State<BottomSheetContentObstacles> createState() =>
      _BottomSheetContentObstaclesState();
}

class _BottomSheetContentObstaclesState
    extends State<BottomSheetContentObstacles> {
  @override
  Widget build(BuildContext context) {
    final notifier = Provider.of<ApiNotifiers>(context);

    return SizedBox(
      height: 400,
      child: Column(
        children: [
          const SizedBox(
            height: 70,
            child: Center(
              child: Text(
                "Select obstacle to delete:",
                textAlign: TextAlign.center,
              ),
            ),
          ),
          const Divider(thickness: 1),
          Expanded(
            child: ListView.builder(
              itemCount: notifier.obstacles.length,
              itemBuilder: (context, index) {
                var currentObstacle = notifier.obstacles[index];
                return ListTile(
                  leading:
                      const Icon(Icons.add_road_rounded, color: Colors.yellow),
                  title: const Text("Obstacle At: "),
                  subtitle: Text("[${currentObstacle.x}:${currentObstacle.y}]"),
                  trailing: IconButton(
                      tooltip: "Delete",
                      onPressed: () async {
                        showDialog(
                          context: context,
                          builder: (BuildContext context) => ConfirmDeleteObs(
                              context,
                              obstacles:
                                  List.generate(1, (index) => currentObstacle)),
                        );
                      },
                      icon: const Icon(
                        Icons.delete,
                        color: Colors.red,
                      )),
                );
              },
            ),
          ),
        ],
      ),
    );
  }
}
