package net.dirtcraft.plugins.dirtoceanblock.utils.gradient;

@FunctionalInterface
public interface Interpolator {

	double[] interpolate(double from, double to, int max);
}