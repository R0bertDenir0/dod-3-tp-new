package grx.dod.demo.tp.types.objects;

import java.util.List;

public interface Pipeline<T> {
	
	List<T> output(List<T> input);

}
