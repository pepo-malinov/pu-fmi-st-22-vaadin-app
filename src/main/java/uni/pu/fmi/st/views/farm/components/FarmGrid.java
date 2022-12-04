package uni.pu.fmi.st.views.farm.components;


import com.vaadin.flow.component.grid.Grid;
import uni.pu.fmi.st.data.entity.Farm;
import uni.pu.fmi.st.data.entity.Owner;

import java.util.List;


public class FarmGrid extends Grid<Farm>
{

    private final List<Farm> farms;


    public FarmGrid(List<Farm> farms){
        super(Farm.class, false);
        this.farms=farms;
        init();
    }


    private void init()
    {
        addColumn(Farm::getName).setFrozen(true).setResizable(true).setSortable(true).setHeader("Име");
        addColumn(Farm::getAddress).setResizable(true).setSortable(true).setHeader("Адрес");
        addColumn(farm->farm.getManager().getName()).setResizable(true).setSortable(true).setHeader("Фермер");
        addColumn(farm->{
            final Owner owner = farm.getOwner();
            return owner.getName()+"/"+owner.getOwnerId();
        }).setResizable(true).setSortable(true).setHeader("Собственик");

        this.setItems(farms);
    }

    public void refreshGrid(List<Farm> farms){
        this.setItems(farms);
    }
}
