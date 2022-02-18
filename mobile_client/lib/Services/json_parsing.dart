import 'dart:convert';

import 'package:fluttericon/font_awesome5_icons.dart';
import 'package:mobile_client/Models/obstacle.dart';
import 'package:mobile_client/Models/result.dart';
import 'package:mobile_client/Models/robot.dart';
import 'package:mobile_client/Models/world.dart';

class Json {
  static List<Robot> parseRobots(String responseBody) {
    final parsed = jsonDecode(responseBody).cast<Map<String, dynamic>>();

    return parsed.map<Robot>((json) => Robot.fromJson(json)).toList();
  }

  static List<World> parseWorlds(String responseBody) {
    final parsed = jsonDecode(responseBody);

    return parsed.map<World>((json) => World.fromJson(json)).toList();
  }

  static World parseWorld(String responseBody) {
    return World.fromJson(jsonDecode(responseBody));
  }

  static Map<List<int>, String> parseWorldMap(String responseBody) {
    Map<List<int>, String> newMap = {};
    var data = jsonDecode(responseBody);

    for (MapEntry e in data.entries) {
      List<int> currentKey = [];

      var tempList = e.key
          .toString()
          .replaceAll('[', '')
          .replaceAll(']', '')
          .replaceAll(' ', '')
          .split(',');
      currentKey.add(int.parse(tempList[0]));
      currentKey.add(int.parse(tempList[1]));

      newMap.putIfAbsent(currentKey, () => e.value);
    }

    return newMap;
  }

  static List<Obstacle> parseObstacles(String responseBody) {
    final parsed = jsonDecode(responseBody).cast<Map<String, dynamic>>();

    return parsed.map<Obstacle>((json) => Obstacle.fromJson(json)).toList();
  }

  static Result parseLaunchStatus(String responseBody) {
    final parsed = jsonDecode(responseBody);
    late String message;

    if (parsed['result'] == 'OK') {
      message = 'Launched successfully';
    } else {
      message = parsed['data']['message'] as String;
    }

    return Result(requestResult: parsed['result'],
        requestMessage: message);
  }

  static Result parseMovementStatus(String responseBody) {
    final parsed = jsonDecode(responseBody);

    return Result(requestResult: parsed['result'],
        requestMessage: parsed['data']['message'] as String);
  }

  static Result parseActionStatus(String responseBody) {
    final parsed = jsonDecode(responseBody);
    return Result(requestResult: parsed['result'], requestMessage: parsed['data']['message'] as String);
  }
}
