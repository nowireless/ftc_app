package org.firstinspires.ftc.teamcode.util;

import android.graphics.Color;

public class HSV {
    public double h_;
    public double s_;
    public double v_;

    public HSV(double h, double s, double v) {
        h_ = h;
        s_ = s;
        v_ = v;
    }

    public static HSV of(RGB rgb) {
        float values[] = {0f, 0f, 0f};
        int red = (int) rgb.r_;
        int green = (int) rgb.g_;
        int blue = (int) rgb.b_;
        Color.RGBToHSV(red, green, blue, values);
        return new HSV(values[0], values[1], values[2]);
    }
}
