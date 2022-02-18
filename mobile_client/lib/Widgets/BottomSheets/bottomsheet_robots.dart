import 'package:flutter/material.dart';
import 'package:mobile_client/Services/api_notifiers.dart';
import 'package:mobile_client/Widgets/Popups/confirmdeleterobot_popup.dart';
import 'package:provider/provider.dart';

class BottomSheetContentRobots extends StatefulWidget {
  const BottomSheetContentRobots({Key? key}) : super(key: key);

  @override
  State<BottomSheetContentRobots> createState() =>
      _BottomSheetContentRobotsState();
}

class _BottomSheetContentRobotsState extends State<BottomSheetContentRobots> {
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
                "Select robot to delete:",
                textAlign: TextAlign.center,
              ),
            ),
          ),
          const Divider(thickness: 1),
          Expanded(
            child: ListView.builder(
              itemCount: notifier.robots.length,
              itemBuilder: (context, index) {
                return ListTile(
                  leading: const Icon(Icons.android, color: Colors.green),
                  title: Text(notifier.robots[index].name),
                  subtitle: Text(notifier.robots[index].state.toString()),
                  trailing: IconButton(
                      tooltip: "Delete robot",
                      onPressed: () async {
                        showDialog(
                          context: context,
                          builder: (BuildContext context) => ConfirmDelete(
                              context,
                              name: notifier.robots[index].name),
                        );
                        setState(() {});
                      },
                      icon: const Icon(Icons.delete, color: Colors.red)),
                );
              },
            ),
          ),
        ],
      ),
    );
  }
}
