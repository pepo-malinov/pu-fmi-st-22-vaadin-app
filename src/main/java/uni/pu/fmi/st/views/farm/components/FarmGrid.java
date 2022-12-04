package uni.pu.fmi.st.views.farm.components;


import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.apache.commons.lang3.StringUtils;
import uni.pu.fmi.st.data.entity.Farm;
import uni.pu.fmi.st.data.entity.Owner;
import uni.pu.fmi.st.data.entity.User;

import java.util.List;


public class FarmGrid extends Grid<Farm>
{

    private final List<Farm> farms;
    private ListDataProvider<Farm> farmListDataProvider;


    public FarmGrid(List<Farm> farms)
    {
        super(Farm.class, false);
        this.farms = farms;
        init();
    }


    private void init()
    {
        final Column<Farm> nameColumn = addColumn(Farm::getName).setFrozen(true).setResizable(true).setSortable(true).setHeader("Име");
        addColumn(Farm::getAddress).setResizable(true).setSortable(true).setHeader("Адрес");
        addColumn(farm -> {
            final User manager = farm.getManager();
            if (manager != null)
            {
                return manager.getName();
            }
            return "";
        }).setResizable(true).setSortable(true).setHeader("Фермер");
        addColumn(farm -> {
            final Owner owner = farm.getOwner();
            return owner.getName() + "/" + owner.getOwnerId();
        }).setResizable(true).setSortable(true).setHeader("Собственик");

        HeaderRow headerRow = appendHeaderRow();
        final TextField nameFilterField = new TextField();
        nameFilterField.setPlaceholder("Въведете име за филтър");
        nameFilterField.setValueChangeMode(ValueChangeMode.EAGER);
        nameFilterField.setClearButtonVisible(true);
        nameFilterField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        nameFilterField.setWidthFull();
        nameFilterField.getStyle().set("max-width", "100%");
        nameFilterField.addValueChangeListener(
                        e -> {
                            farmListDataProvider.clearFilters();
                            farmListDataProvider.addFilter(f -> {
                                                               final String name = f.getName().toLowerCase();
                                                               final String value = e.getValue();
                                                               if (StringUtils.isNotBlank(value))
                                                               {
                                                                   return name.contains(value.toLowerCase());
                                                               }
                                                               return true;
                                                           }
                            );
                        }
        );
        headerRow.getCell(nameColumn).setComponent(nameFilterField);
        refreshGrid(farms);
    }


    public void refreshGrid(List<Farm> farms)
    {
        farmListDataProvider = new ListDataProvider<>(farms);
        this.setItems(farmListDataProvider);
    }
}
