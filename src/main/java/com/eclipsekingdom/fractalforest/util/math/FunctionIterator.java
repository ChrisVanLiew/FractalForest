package com.eclipsekingdom.fractalforest.util.math;
import com.eclipsekingdom.fractalforest.util.math.Point2D;
import com.eclipsekingdom.fractalforest.util.math.SegmentIterator;
import com.eclipsekingdom.fractalforest.util.math.functions.Function;
import com.eclipsekingdom.fractalforest.util.math.Plane;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class FunctionIterator extends ArrayList<Block> {

    public FunctionIterator(World world, Vector origin, Plane plane, double length, double radius, Function function){
        Point2D prevPoint = function.pointAt(0);
        Point2D point = function.pointAt(1);
        double currentLength = prevPoint.distance(point);

        while(currentLength < length){
            Vector sBegin = origin.clone().add(plane.translate(prevPoint.getX(), prevPoint.getY()));
            Vector sEnd = origin.clone().add(plane.translate(point.getX(),point.getY()));

            for(Block b: new SegmentIterator(world, sBegin, sEnd, radius)){
                if(!contains(b)){
                    add(b);
                }
            }

            currentLength += prevPoint.distance(point);
            prevPoint = point;
            point = function.pointAt(point.getX() + 1);
        }
    }

}
