import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:mobile_client/Services/api_notifiers.dart';
import 'package:mobile_client/Widgets/BottomSheets/bottomsheet_addobstacles.dart';
import 'package:mobile_client/Widgets/BottomSheets/bottomsheet_obstacles.dart';
import 'package:mobile_client/Widgets/BottomSheets/bottomsheet_robots.dart';
import 'package:mobile_client/Widgets/BottomSheets/bottomsheet_worlds.dart';
import 'package:mobile_client/Widgets/Popups/saveworld_popup.dart';
import 'package:provider/provider.dart';

class AdminPage extends StatefulWidget {
  final String title;

  const AdminPage({Key? key, required this.title}) : super(key: key);

  @override
  _AdminPageState createState() => _AdminPageState();
}

class _AdminPageState extends State<AdminPage> {
  @override
  void initState() {
    super.initState();
    Provider.of<ApiNotifiers>(context, listen: false).updateRobots();
    Provider.of<ApiNotifiers>(context, listen: false).updateObstacles();
  }

  @override
  Widget build(BuildContext context) {
    void _showRobotsBottomSheet(BuildContext context) {
      showModalBottomSheet(
        context: context,
        builder: (context) {
          return const BottomSheetContentRobots();
        },
      );
    }

    void _showWorldsBottomSheet(BuildContext context) {
      showModalBottomSheet(
        context: context,
        builder: (context) {
          return const BottomSheetContentWorlds();
        },
      );
    }

    void _showObstaclesBottomSheet(BuildContext context) {
      showModalBottomSheet(
        context: context,
        builder: (context) {
          return const BottomSheetContentObstacles();
        },
      );
    }

    void _showAddObstaclesBottomSheet(BuildContext context) {
      showModalBottomSheet(
        context: context,
        isScrollControlled: true,
        builder: (context) {
          return const BottomSheetContentAddObstacles();
        },
      );
    }

    return Scaffold(
      backgroundColor: Colors.teal[100],
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: SafeArea(
        child: ListView(
          restorationId: 'API Buttons',
          padding: const EdgeInsets.all(16),
          children: [
            Container(
              margin: const EdgeInsets.all(6.0),
              child: CupertinoButton.filled(
                onPressed: () async {
                  await Provider.of<ApiNotifiers>(context, listen: false)
                      .updateRobots();
                  _showRobotsBottomSheet(context);
                },
                child: const Text(
                  'ROBOTS',
                ),
              ),
            ),
            Container(
              margin: const EdgeInsets.all(6.0),
              child: CupertinoButton.filled(
                onPressed: () async {
                  Provider.of<ApiNotifiers>(context, listen: false)
                      .updateObstacles();
                  _showObstaclesBottomSheet(context);
                },
                child: const Text(
                  // POST /admin/obstacles/ + {body with a list of obstacles}
                  // DELETE /admin/obstacles + {body with a list of obstacles}
                  'OBSTACLES',
                ),
              ),
            ),
            Container(
              margin: const EdgeInsets.all(6.0),
              child: CupertinoButton.filled(
                onPressed: () async {
                  await Provider.of<ApiNotifiers>(context, listen: false)
                      .updateWorlds();
                  _showWorldsBottomSheet(context);
                },
                child: const Text(
                  // GET /admin/load/{world-name}
                  'WORLDS',
                ),
              ),
            ),
            Container(
              margin: const EdgeInsets.all(6.0),
              // bac
              child: CupertinoButton.filled(
                onPressed: () async {
                  showDialog(
                    context: context,
                    builder: (BuildContext context) => SavedAlert(context),
                  );
                },
                child: const Text(
                  // POST /admin/save/{world-name}
                  'SAVE CURRENT WORLD',
                ),
              ),
            ),
            Container(
              margin: const EdgeInsets.all(6.0),
              child: CupertinoButton.filled(
                onPressed: () async {
                  await Provider.of<ApiNotifiers>(context, listen: false)
                      .updateObstacles();
                  _showAddObstaclesBottomSheet(context);
                },
                child: const Text(
                  // GET /admin/load/{world-name}
                  'ADD OBSTACLES',
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
