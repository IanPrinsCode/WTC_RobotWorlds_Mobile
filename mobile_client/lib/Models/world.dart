import 'package:mobile_client/Services/json_parsing.dart';

class World {
  late final String name;
  late final int size;
  late final Map<List<int>, String> map;

  World({
    required this.name,
    required this.size,
    required this.map,
  });

  factory World.fromJson(Map<String, dynamic> json) {
    return World(
      name: json['name'] as String,
      size: json['size'] as int,
      map: Json.parseWorldMap(json['world']),
    );
  }
}
