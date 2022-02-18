class Robot {
  late final String name;
  Map state;

  Robot({required this.name, required this.state});

  setState(Map newState) {
    state = newState;
  }

  factory Robot.fromJson(Map<String, dynamic> json) {
    return Robot(
      name: json['name'] as String,
      state: json['state'] as Map,
    );
  }
}
