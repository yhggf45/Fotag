import javax.vecmath.Point2d;
import java.awt.*;
import java.io.Serializable;
import java.util.Observable;
import java.util.ArrayList;

public class Model extends Observable implements Serializable {
    private ArrayList<Shape> shape_collection = new ArrayList<Shape>();
    private Color select_color = Color.red;
    private float select_thickness = 3.0f;

    private int slider_pre = 0;
    private ArrayList<Shape> print_shape_collection = new ArrayList<Shape>();

    Model() {
        setChanged();
    }

    public void set_play_prentage(int i) {
        slider_pre = i;
        update_collection();
        setChanged();
        notifyObservers();
    }

    public int get_slider_pre() {
        return slider_pre;
    }

    public void update_collection() {
        if (slider_pre == 100) {return;}

        int point_count = 0;
        for (Shape ss: shape_collection) {
            if (ss.get_points() != null) {
                point_count += ss.get_points().size();
            }
        }

        int need_print = (int) (point_count * slider_pre / 100);
        //System.out.println("total: " + Integer.toString(point_count));
        //System.out.println("need to print: " + Integer.toString(need_print));
        int keep_track = 0;
        int i;
        for (i = 0; keep_track <= need_print; ++i) {
            if (shape_collection.get(i).get_points() != null) {
                keep_track += shape_collection.get(i).get_points().size();
                System.out.println("add : " + Integer.toString(shape_collection.get(i).get_points().size()));
            }
        }
        if (i == 0) {return;}
        keep_track -= shape_collection.get(i-1).get_points().size();
        i = i - 1;

        System.out.println("total shapes: " + Integer.toString(shape_collection.size()));
        print_shape_collection.clear();
        for(int t = 0; t < i; ++t) {
            print_shape_collection.add(shape_collection.get(t));
        }
        System.out.println("need to print shapes : " + Integer.toString(print_shape_collection.size()));
        Shape temp = new Shape();
        temp.setColour(shape_collection.get(i).getColour());
        temp.set_thickness(shape_collection.get(i).getStrokeThickness());
        temp.setScale(1.0f);
        for(int a = 0; a <= need_print - keep_track; ++a) {
            temp.addPoint(shape_collection.get(i).get_points().get(a));
        }
        print_shape_collection.add(temp);
    }

    public void add_shape(Shape s) {
        //System.out.println("before: " + Integer.toString(shape_collection.size()));
        shape_collection.add(s);
        //System.out.println("before: " + Integer.toString(shape_collection.size()));
        print_shape_collection.add(s);
        slider_pre = 100;
        setChanged();
        notifyObservers();
    }
    
    public void clear_collection() {
        shape_collection.clear();
        setChanged();
        notifyObservers();
    }

    public ArrayList<Shape> get_shape_collection() {
        return shape_collection;
    }

    public ArrayList<Shape> get_print_shape_collection() {
        return print_shape_collection;
    }

    public void change_color(Color c) {
        select_color = c;
        System.out.println("changed" + c.toString());
        setChanged();
        notifyObservers();
    }

    public Color get_color() {
        return select_color;
    }

    public void change_thickness(float f) {
        select_thickness = f;
        System.out.println("changed " + Float.toString(f));
        setChanged();
        notifyObservers();
    }

    public float get_thickness() {
        return select_thickness;
    }
}
