import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:mobile_client/Views/player_portal_3.dart';
import 'package:mobile_client/Widgets/Dynamic/bordered_text.dart';

import 'admin_page_3.dart';

class TogglePage extends StatefulWidget {
  final String title;

  const TogglePage({Key? key, required this.title}) : super(key: key);

  @override
  _TogglePageState createState() => _TogglePageState();
}

class _TogglePageState extends State<TogglePage> {
  // var shineShadow = ShineShadow();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.blueGrey,
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Container(
        padding: const EdgeInsets.all(10),
        child: Column(
          children: [
            Expanded(
              child: InkWell(
                onTap: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(builder: (context) => const PlayerPage(title: "PLAYER")),
                  );
                },
                child: Container(
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(10),
                    color: Colors.teal[100],
                  ),
                  width: MediaQuery.of(context).size.width,
                  height: MediaQuery.of(context).size.height,
                  child: const Center(
                      child: BorderedText(
                    text: "PLAYER",
                    fillColor: Colors.cyanAccent,
                    borderColor: Colors.blueGrey,
                  )),
                ),
              ),
            ),
            const Divider(
              color: Colors.blueGrey,
              thickness: 1,
              indent: 20,
              endIndent: 0,
              // width: 20,
            ),
            Expanded(
              child: InkWell(
                onTap: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(builder: (context) => const AdminPage(title: "ADMIN")),
                  );
                },
                child: Container(
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(10),
                    color: Colors.teal[100],
                  ),
                  width: MediaQuery.of(context).size.width,
                  height: MediaQuery.of(context).size.height,
                  child: const Center(
                      child: BorderedText(
                    text: "ADMINISTRATION",
                    fillColor: Colors.cyanAccent,
                    borderColor: Colors.blueGrey,
                  )),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
