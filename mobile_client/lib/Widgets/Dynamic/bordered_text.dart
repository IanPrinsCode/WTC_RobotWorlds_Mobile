import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class BorderedText extends StatelessWidget {
  final String text;
  final Color borderColor;
  final Color fillColor;

  const BorderedText({Key? key, required this.text, required this.borderColor, required this.fillColor}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: <Widget>[
        // Stroked text as border.
        Text(
          text,
          style: TextStyle(
            fontSize: 40,
            foreground: Paint()
              ..style = PaintingStyle.stroke
              ..strokeWidth = 5
              ..color = borderColor,
          ),
        ),
        // Solid text as fill.
        Text(
          text,
          style: TextStyle(
            fontSize: 40,
            color: fillColor,
          ),
        ),
      ],
    );
  }
}
