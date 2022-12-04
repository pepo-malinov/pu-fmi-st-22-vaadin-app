package uni.pu.fmi.st.views.owner;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import uni.pu.fmi.st.data.entity.Farm;
import uni.pu.fmi.st.data.entity.Owner;
import uni.pu.fmi.st.data.service.FarmService;
import uni.pu.fmi.st.data.service.OwnerService;
import uni.pu.fmi.st.data.service.UserService;
import uni.pu.fmi.st.views.MainLayout;
import uni.pu.fmi.st.views.farm.components.FarmForm;
import uni.pu.fmi.st.views.farm.components.FarmGrid;
import uni.pu.fmi.st.views.owner.components.OwnerForm;

import java.util.ArrayList;


@PageTitle("Owner-Overview")
@AnonymousAllowed
@Route(value = "owners", layout = MainLayout.class)
public class OwnerView extends VerticalLayout
{
    private final FarmService farmService;
    private final UserService userService;
    private OwnerService ownerService;
    private Button addButton;
    private Button editButton;
    private Button removeButton;
    private Grid<Owner> grid;
    private FarmGrid farmGrid;
    private Button addFarm;


    @Autowired
    public OwnerView(OwnerService ownerService, FarmService farmService, UserService userService)
    {
        this.ownerService = ownerService;
        this.farmService = farmService;
        this.userService = userService;
        init();
    }


    private void init()
    {
        add(new Label("Собственици"));
        addOwnerButtons();
        addOwnerGrid();
        addFarm = new Button("Добави Ферма", l -> {
            final Farm farm = new Farm();
            farm.setOwner(grid.asSingleSelect().getValue());
            openFarmForm(farm);
        });
        add(new HorizontalLayout(addFarm));
        farmGrid = new FarmGrid(new ArrayList<>());
        add(farmGrid);
        setSizeFull();
    }


    private void addOwnerGrid()
    {
        grid = new Grid<>(Owner.class, false);
        grid.addColumn(Owner::getName).setHeader("Име").setSortable(true).setFrozen(true).setResizable(true);
        grid.addColumn(Owner::getOwnerId).setHeader("ЕГН/Булстат").setSortable(true);
        grid.addColumn(Owner::getEmail).setHeader("Email").setSortable(true);
        grid.addColumn(Owner::getGsm).setHeader("Tел.").setSortable(true);

        refreshGridData();
        grid.asSingleSelect().addValueChangeListener(l -> {
            final Owner value = l.getValue();
            editButton.setEnabled(value != null);
            removeButton.setEnabled(value != null);
            addFarm.setEnabled(value != null);
            refreshFarmGridData();
        });
        add(grid);
    }


    private void refreshGridData()
    {
        grid.setItems(query -> ownerService.list(
                        PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                                           .stream());
    }


    private void addOwnerButtons()
    {
        addButton = new Button("Добави", l -> {
            final Owner owner = new Owner();
            openOwnerForm(owner);
        });
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        editButton = new Button("Промени", l -> {
            final Owner owner = grid.asSingleSelect().getValue();
            openOwnerForm(owner);
        });
        editButton.setEnabled(false);
        editButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        removeButton = new Button("Изтрий", l -> {
            final Dialog dialog = new Dialog();
            final Button yes = new Button("Да", removeL -> {
                ownerService.delete(grid.asSingleSelect().getValue().getId());
                dialog.close();
                refreshGridData();
            });
            final Button no = new Button("Не", removeL -> dialog.close());
            final HorizontalLayout removeDialogButtons = new HorizontalLayout(yes, no);
            dialog.setWidth("350px");
            dialog.setHeight("200px");
            dialog.add(new VerticalLayout(new H3("Ама наистина ли ще го триеш?"), removeDialogButtons));
            dialog.open();
        });
        removeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        removeButton.setEnabled(false);
        final HorizontalLayout buttons = new HorizontalLayout(addButton, editButton, removeButton);
        add(buttons);
    }


    private void openOwnerForm(Owner owner)
    {
        final Dialog dialog = new Dialog();
        final OwnerForm ownerForm = new OwnerForm(owner);
        ownerForm.addCancelClickListener(closeL -> dialog.close());
        ownerForm.addSaveClickListener(saveL -> {
            if (ownerForm.isSaved())
            {
                ownerService.update(owner);
                dialog.close();
                refreshGridData();
            }
        });
        dialog.add(ownerForm);
        dialog.setHeight("450px");
        dialog.setWidth("300px");
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(true);
        dialog.open();
    }


    private void openFarmForm(Farm farm)
    {
        final Dialog dialog = new Dialog();
        final FarmForm farmForm = new FarmForm(farm, userService);
        farmForm.addCancelClickListener(closeL -> dialog.close());
        farmForm.addSaveClickListener(saveL -> {
            if(farmForm.isSaved()){
                farmService.update(farm);
                dialog.close();
                refreshFarmGridData();
            }
        });
        dialog.add(farmForm);
        dialog.setHeight("450px");
        dialog.setWidth("300px");
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(true);
        dialog.open();
    }


    private void refreshFarmGridData()
    {
        farmGrid.refreshGrid(farmService.findByOwner(grid.asSingleSelect().getValue()));
    }
}
