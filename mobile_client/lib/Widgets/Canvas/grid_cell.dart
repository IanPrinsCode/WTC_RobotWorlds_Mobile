import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:mobile_client/Widgets/Cards/world_card.dart';

class Cell extends StatelessWidget {
  final int row, col, size;
  final Map<List<int>, String> worldMap;

  const Cell(this.row, this.col, this.worldMap, this.size, {Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return InkResponse(
      enableFeedback: true,
      child: SizedBox(
        width: 30,
        height: 30,
        child: Container(
          color: Colors.white,
          child: Center(
            child: Block(
              object: getValue(worldMap, row, col, size)!,
              cell: List<int>.from([_columnToX(col, size), _rowToY(row, size)]),
            ),
          ),
        ),
      ),
    );
  }
}

String? getValue(Map<List<int>, String> worldMap, int row, int col, int size) {
  if (size == 1) {
    size = 0;
  }
  for (var key in worldMap.keys) {
    if (key[0] == _columnToX(col, size) && key[1] == _rowToY(row, size)) {
      return worldMap[key];
    }
  }
  return "null";
}

int _columnToX(int col, int size) {
  return col - ((size + 3) ~/ 2);
}

int _rowToY(int row, int size) {
  return -1 * (row - ((size + 3) ~/ 2));
}
