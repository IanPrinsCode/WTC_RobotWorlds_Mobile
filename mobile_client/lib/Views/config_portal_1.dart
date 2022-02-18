import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:mobile_client/Services/api_requests.dart';
import 'package:mobile_client/Views/toggle_page_2.dart';

class ConfigurationPortal extends StatefulWidget {
  final String title;

  const ConfigurationPortal({Key? key, required this.title}) : super(key: key);

  @override
  State<ConfigurationPortal> createState() => _ConfigurationPortalState();
}

class _ConfigurationPortalState extends State<ConfigurationPortal> {
  var ipController = TextEditingController();
  var portController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.teal[100],
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: SafeArea(
        child: ListView(
          restorationId: 'Config Box',
          padding: const EdgeInsets.all(16),
          children: [
            Padding(
              padding: const EdgeInsets.symmetric(vertical: 8),
              child: TextField(
                controller: ipController,
                textInputAction: TextInputAction.next,
                keyboardType: TextInputType.emailAddress,
                autocorrect: false,
                decoration: const InputDecoration(hintText: "IP Address"),
              ),
            ),
            Padding(
              padding: const EdgeInsets.symmetric(vertical: 8),
              child: TextField(
                controller: portController,
                textInputAction: TextInputAction.next,
                autocorrect: false,
                decoration: const InputDecoration(hintText: "Port Number"),
              ),
            ),
            const SizedBox(height: 16),
            Container(
              margin: const EdgeInsets.all(6.0),
              child: CupertinoButton.filled(
                onPressed: () {
                  if (ipController.text.isEmpty &&
                      portController.text.isEmpty) {
                    portController.text = '5001';
                    ipController.text = 'localhost';
                  }
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                        builder: (context) => const TogglePage(
                            title: "Choose your destination:")),
                  );
                  Http.port = portController.text;
                  Http.ip = ipController.text;
                },
                child: const Text(
                  'OK',
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
