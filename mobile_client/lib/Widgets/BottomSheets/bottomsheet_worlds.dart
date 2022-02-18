import 'package:flutter/material.dart';
import 'package:mobile_client/Services/api_notifiers.dart';
import 'package:mobile_client/Widgets/Popups/confirmload_popup.dart';
import 'package:provider/provider.dart';

class BottomSheetContentWorlds extends StatefulWidget {
  const BottomSheetContentWorlds({Key? key}) : super(key: key);

  @override
  State<BottomSheetContentWorlds> createState() =>
      _BottomSheetContentWorldsState();
}

class _BottomSheetContentWorldsState extends State<BottomSheetContentWorlds> {
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
                "Select world to load:",
                textAlign: TextAlign.center,
              ),
            ),
          ),
          const Divider(thickness: 1),
          Expanded(
            child: ListView.builder(
              itemCount: notifier.worlds.length,
              itemBuilder: (context, index) {
                return ListTile(
                  leading: const Icon(Icons.add_location_alt,
                      color: Colors.blueAccent),
                  title: Text(notifier.worlds[index].name),
                  subtitle: Text("World Size: "
                      "${notifier.worlds[index].size}x${notifier.worlds[index].size}"
                      "\nWorld: ${notifier.worlds[index].map.toString()}"),
                  trailing: IconButton(
                      tooltip: "Load world",
                      onPressed: () async {
                        showDialog(
                          context: context,
                          builder: (BuildContext context) => ConfirmLoad(
                              context,
                              name: notifier.worlds[index].name),
                        );
                      },
                      icon: const Icon(
                        Icons.cloud_download_rounded,
                        color: Colors.blueGrey,
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
