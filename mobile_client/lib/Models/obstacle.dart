class Obstacle {
  late final int x;
  late final int y;

  Obstacle({required this.x, required this.y});

  factory Obstacle.fromJson(Map<String, dynamic> json) {
    return Obstacle(
      x: json['x'] as int,
      y: json['y'] as int,
    );
  }

  Map<String, dynamic> toJson() {
    return <String, dynamic>{
      'x': x,
      'y': y,
    };
  }
}
