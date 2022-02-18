import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:mobile_client/Models/world.dart';

import 'grid_cell.dart';

class WorldMap extends StatefulWidget {
  WorldMap(this.currentWorld, {Key? key}) : super(key: key);

  World currentWorld;

  @override
  State<WorldMap> createState() => _WorldMapState();
}

class _WorldMapState extends State<WorldMap> {
  @override
  Widget build(BuildContext context) {
    return Table(
      border: const TableBorder(
        left: BorderSide(width: 3.0, color: Colors.black),
        top: BorderSide(width: 3.0, color: Colors.black),
        right: BorderSide(width: 3.0, color: Colors.black),
        bottom: BorderSide(width: 3.0, color: Colors.black),
      ),
      defaultVerticalAlignment: TableCellVerticalAlignment.middle,
      children: _getTableRows(),
    );
  }

  List<TableRow> _getTableRows() {
    // print(currentWorld.size);
    return List.generate(_getWorldSize(widget.currentWorld.size), (int rowNumber) {
      return TableRow(children: _getRow(rowNumber));
    });
  }

  List<Widget> _getRow(int rowNumber) {
    return List.generate(_getWorldSize(widget.currentWorld.size), (int colNumber) {
      return Container(
        decoration: const BoxDecoration(
          border: Border(
            right: BorderSide(
              width: 1.0,
              color: Colors.black,
            ),
            bottom: BorderSide(
              width: 1.0,
              color: Colors.black,
            ),
          ),
        ),
        child: Cell(rowNumber, colNumber, widget.currentWorld.map, widget.currentWorld.size),
      );
    });
  }

  int _getWorldSize(int size) {
    if (size == 1) {
      return 3;
    } else if (size % 2 == 0) {
      return size + 3;
    } else {
      return size + 2;
    }
  }
}
