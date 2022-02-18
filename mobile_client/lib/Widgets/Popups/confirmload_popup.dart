import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:mobile_client/Services/api_notifiers.dart';
import 'package:provider/provider.dart';

class ConfirmLoad extends StatelessWidget {
  const ConfirmLoad(BuildContext context, {Key? key, required this.name}) : super(key: key);

  final String name;

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: Center(child: Text("Are you sure you want to load '$name' ?")),
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
                .loadWorld(name);
            Navigator.of(context).pop();
          },
          child: const Text('Continue'),
        ),
      ],
    );
  }
}