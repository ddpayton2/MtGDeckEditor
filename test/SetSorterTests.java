
import com.google.common.collect.Ordering;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class SetSorterTests {

    private final SetSorter sorter = new SetSorter();
    private final List<Set> list = sorter.getList();
    private final boolean isSorted = Ordering.natural().isOrdered(list);

    @Test
    public void setSorterTest(){
        sorter.sort();
        Assert.assertTrue("The list is ordered", isSorted);
    }

}
