package partner.composite;

import java.util.ArrayList;
import java.util.List;

/** "Composite" */
class CompositeGraphic implements Graphic {

	//Collection of child graphics.
	private List<Graphic> mChildGraphics = new ArrayList<Graphic>();

	//Prints the graphic.
	public void print() {
		for (Graphic graphic : mChildGraphics) {
			graphic.print();
		}
	}

	//Adds the graphic to the composition.
	public void add(Graphic graphic) {
		mChildGraphics.add(graphic);
	}

	//Removes the graphic from the composition.
	public void remove(Graphic graphic) {
		mChildGraphics.remove(graphic);
	}
}

