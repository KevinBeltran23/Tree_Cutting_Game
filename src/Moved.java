import processing.core.PImage;

import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public abstract class Moved extends Actioned{
    public Moved(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    /*
    public Point nextPosition(WorldModel world, Point dest) {
        // single step pathing strategy
        PathingStrategy SingleStepPathingStrategy = new SingleStepPathingStrategy();
        List<Point> nextPoints = SingleStepPathingStrategy.computePath(this.getPosition(), dest, canPassThrough(world), null, PathingStrategy.CARDINAL_NEIGHBORS);

        // a star pathing strategy
        PathingStrategy aStarPathingStrategy = new AStarPathingStrategy();
        //List<Point> nextPoints = aStarPathingStrategy.computePath(this.getPosition(), dest, canPassThrough(world), withinReach(), PathingStrategy.CARDINAL_NEIGHBORS);

        if (nextPoints.size() == 0) {
            return getPosition();
        }
        return nextPoints.get(0);
    }
    */

    public Point nextPosition(WorldModel world, Point destPos) {
        int horiz = Integer.signum(destPos.x - this.getPosition().x);
        Point newPos = new Point(this.getPosition().x + horiz, this.getPosition().y);
        if (horiz == 0 || world.isOccupied(newPos) && !(world.getOccupancyCell(newPos) instanceof Stump)) {
            int vert = Integer.signum(destPos.y - this.getPosition().y);
            newPos = new Point(this.getPosition().x, this.getPosition().y + vert);
            if (vert == 0 || world.isOccupied(newPos) && !(world.getOccupancyCell(newPos) instanceof Stump)) {
                newPos = this.getPosition();
            }
        }

        return newPos;
    }

    // currently my fairies are getting stuck on everything. Not doing depth search for all possible path
    // OR maybe the issue is that the paths are being filtered out incorrectly

    private static Predicate<Point> canPassThrough(WorldModel world) {
        return p -> (world.withinBounds(p) && !world.isOccupied(p));
    }

    private static BiPredicate<Point, Point> withinReach() {
        return Point::adjacent;
    }
}
