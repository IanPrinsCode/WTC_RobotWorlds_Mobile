import 'package:flutter/material.dart';
import 'package:mobile_client/Models/obstacle.dart';
import 'package:mobile_client/Widgets/Popups/confirmaddobstacles_popup.dart';

class BottomSheetContentAddObstacles extends StatefulWidget {
  const BottomSheetContentAddObstacles({Key? key}) : super(key: key);

  @override
  State<BottomSheetContentAddObstacles> createState() =>
      _BottomSheetContentObstaclesState();
}

class _BottomSheetContentObstaclesState
    extends State<BottomSheetContentAddObstacles> {
  TextEditingController x = TextEditingController();
  TextEditingController y = TextEditingController();
  final List<Obstacle> _obstacle = [];

  @override
  Widget build(BuildContext context) {
    return FractionallySizedBox(
      heightFactor: 1,
      child: Column(
        children: [
          const SizedBox(
            height: 70,
            child: Center(
              child: Text(
                "Enter obstacle to add:",
                textAlign: TextAlign.center,
              ),
            ),
          ),
          const Divider(thickness: 1),
          Expanded(
            child: ListView.builder(
              itemCount: 1,
              itemBuilder: (context, index) {
                return Row(
                  children: [
                    Expanded(
                      child: TextField(
                        controller: x,
                        decoration: const InputDecoration(
                          border: OutlineInputBorder(),
                          labelText: "X",
                        ),
                      ),
                    ),
                    Expanded(
                      child: TextField(
                        controller: y,
                        decoration: const InputDecoration(
                          border: OutlineInputBorder(),
                          labelText: "Y",
                        ),
                      ),
                    ),
                    IconButton(
                        icon: const Icon(Icons.add),
                        onPressed: () {
                          if (x.text.isNotEmpty && y.text.isNotEmpty) {
                            setState(() {
                              _obstacle.add(Obstacle(
                                x: int.parse(x.text),
                                y: int.parse(y.text),
                              ));
                              x.clear();
                              y.clear();
                            });
                          }
                        }),
                  ],
                );
              },
            ),
          ),
          Expanded(
              child: ListView.builder(
                  itemCount: _obstacle.length,
                  itemBuilder: (context, index) {
                    return ListTile(
                      title: Text(
                          "Obstacle At: ${_obstacle[index].x}, ${_obstacle[index].y}"),
                      trailing: IconButton(
                        icon: const Icon(Icons.delete, color: Colors.red),
                        onPressed: () {
                          setState(() {
                            _obstacle.removeAt(index);
                          });
                        },
                      ),
                    );
                  })),
          Padding(
            padding: const EdgeInsetsDirectional.all(10),
            child: Align(
                alignment: Alignment.bottomCenter,
                child: ElevatedButton(
                  onPressed: () {
                    showDialog(
                        context: context,
                        builder: (BuildContext context) => ConfirmAdd(
                          context,
                          obstacles: _obstacle,
                        ));
                  },
                  child: const Text("Submit"),
                )),
          ),
        ],
      ),
    );
  }
}
