import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:mobile_client/Models/obstacle.dart';
import 'package:mobile_client/Models/result.dart';
import 'package:mobile_client/Models/robot.dart';
import 'package:mobile_client/Models/world.dart';

import 'json_parsing.dart';

class Http {
  static late final String port;
  static late final String ip;
  static const Map<String, String> requestHeader = {
    "Content-type": "application/json",
    "Accept": "application/json",
    "Access-Control_Allow_Origin": "*"
  };
  static final String baseUrl = "http://$ip:$port";

  Future<List<Robot>> getRobots() async {
    var url = Uri.parse("$baseUrl/admin/robots");
    var response = await http.get(url, headers: requestHeader);

    _checkResponse(response, "getRobots");

    return Json.parseRobots(response.body);
  }

  Future<World> getCurrentWorld() async {
    var url = Uri.parse("$baseUrl/world");
    var response = await http.get(url, headers: requestHeader);

    _checkResponse(response, "Could not get current world!");

    return Json.parseWorld(response.body);
  }

  Future<List<World>> getWorlds() async {
    var url = Uri.parse("$baseUrl/admin/worlds");
    var response = await http.get(url, headers: requestHeader);

    _checkResponse(response, "Could not get worlds list!");

    return Json.parseWorlds(response.body);
  }

  Future<void> deleteRobot(String name) async {
    var url = Uri.parse("$baseUrl/admin/robot/$name");
    var response = await http.delete(url, headers: requestHeader);

    _checkResponse(response, "Could not delete robot!");
  }

  Future<World> loadWorld(String name) async {
    var url = Uri.parse("$baseUrl/admin/load/$name");
    var response = await http.get(url, headers: requestHeader);

    _checkResponse(response, "Could not load specified world!");

    return Json.parseWorld(response.body);
  }

  Future<void> saveWorld(String name) async {
    var url = Uri.parse("$baseUrl/admin/save/$name");
    var response = await http.post(url, headers: requestHeader);

    _checkResponse(response, "Could not save world!");
  }

  Future<List<Obstacle>> getObstacles() async {
    var url = Uri.parse("$baseUrl/admin/obstacles");
    var response = await http.get(url, headers: requestHeader);

    _checkResponse(response, "Could not get obstacles list!");

    return Json.parseObstacles(response.body);
  }

  Future<void> removeObstacles(List<Obstacle> obstacles) async {
    var url = Uri.parse("$baseUrl/admin/obstacles");

    var response = await http.delete(url,
        headers: requestHeader, body: json.encode(obstacles));
    _checkResponse(response, "Could not remove obstacles!");
  }

  Future<void> addObstacles(List<Obstacle> obstacles) async {
    var url = Uri.parse("$baseUrl/admin/obstacles");

    var response = await http.post(url,
        headers: requestHeader, body: json.encode(obstacles));

    _checkResponse(response, "Could not add obstacles!");
  }

  Future<Result> launchRobot(String name, String robotType) async {
    var url = Uri.parse("$baseUrl/robot/$name");
    var launchBody = "{\"robot\":\"" +
        name +
        "\"," +
        "\"command\": \"launch\"," +
        "\"arguments\": [\"" +
        robotType.toLowerCase() +
        "\",\"5\",\"5\"]" +
        "}";
    var response =
        await http.post(url, headers: requestHeader, body: launchBody);

    _checkResponse(response, "Could not launch robot!");

    return Json.parseLaunchStatus(response.body);
  }

  Future<Result> robotMove(String name, String direction) async {
    var url = Uri.parse("$baseUrl/robot/$name");
    var launchBody = "{\"robot\":\"" +
        name +
        "\"," +
        "\"command\": \"$direction\"," +
        "\"arguments\": [1]" +
        "}";
    var response =
        await http.post(url, headers: requestHeader, body: launchBody);

    _checkResponse(response, "Could not move robot!");

    return Json.parseMovementStatus(response.body);
  }

  Future<Result> robotActions(String name, String action) async {
    var url = Uri.parse("$baseUrl/robot/$name");
    var launchBody = "{\"robot\":\"" +
        name +
        "\"," +
        "\"command\": \"" +
        action +
        "\"," +
        "\"arguments\": []" +
        "}";
    var response =
        await http.post(url, headers: requestHeader, body: launchBody);

    _checkResponse(response, "Could not complete action: $action");

    return Json.parseActionStatus(response.body);
  }

  Future<void> robotTurn(String name, String direction) async {
    var url = Uri.parse("$baseUrl/robot/$name");
    var launchBody = "{\"robot\":\"" +
        name +
        "\"," +
        "\"command\": \"turn\"," +
        "\"arguments\": [\"" +
        direction +
        "\"]" +
        "}";
    var response =
        await http.post(url, headers: requestHeader, body: launchBody);

    _checkResponse(response, "Could not turn right!");
  }
}

_checkResponse(http.Response response, String message) {
  if (!response.statusCode.toString().startsWith("2")) {
    throw Exception(message);
  }
}
