import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:mobile_client/Services/api_notifiers.dart';
import 'package:provider/provider.dart';

class SavedAlert extends StatelessWidget {
  const SavedAlert(BuildContext context, {Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    var worldNameController = TextEditingController();

    return AlertDialog(
      title: const Center(child: Text('Enter name to save as:')),
      content: Column(
        mainAxisSize: MainAxisSize.min,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Padding(
            padding: const EdgeInsets.symmetric(vertical: 8),
            child: TextField(
              controller: worldNameController,
              textInputAction: TextInputAction.next,
              autocorrect: false,
              decoration: const InputDecoration(hintText: "world name"),
            ),
          ),
        ],
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
            await Provider.of<ApiNotifiers>(context, listen: false).saveWorld(worldNameController.text);
            Navigator.of(context).pop();
          },
          child: const Text('Save'),
        ),
      ],
    );
  }
}
