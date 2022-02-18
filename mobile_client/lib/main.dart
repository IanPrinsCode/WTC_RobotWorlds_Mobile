import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import 'Services/api_notifiers.dart';
import 'Views/config_portal_1.dart';

void main() {
  runApp(ChangeNotifierProvider(
      child: const MyApp(),
      create: (_) => ApiNotifiers()
  ));
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Robot Worlds',
      theme: ThemeData(
        primarySwatch: Colors.blueGrey,
      ),
      home: const ConfigurationPortal(title: 'Server configuration portal'),
    );
  }
}


