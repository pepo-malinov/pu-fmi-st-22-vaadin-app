package uni.pu.fmi.st.views.farm.components;


import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import uni.pu.fmi.st.data.entity.Farm;
import uni.pu.fmi.st.data.entity.Owner;
import uni.pu.fmi.st.data.entity.User;
import uni.pu.fmi.st.data.service.UserService;


public class FarmForm extends VerticalLayout
{
    private final UserService userService;
    private TextField name = new TextField("Име");
    private TextField ownerName = new TextField("ЕГН/Булстат");
    private TextField address = new TextField("Адрес");
    private ComboBox<User> manager = new ComboBox("Потребители");
    private boolean saved;

    private Farm farm;
    private BeanValidationBinder<Farm> binder = new BeanValidationBinder<>(Farm.class);
    private Button cancel;
    private Button save;


    public FarmForm(Farm farm, UserService userService)
    {
        this.farm = farm;
        this.userService = userService;
        init();
    }


    private void init()
    {
        setSizeFull();
        final FormLayout form = new FormLayout();
        manager.setItems(userService.findAll());
        manager.setItemLabelGenerator(User::getName);
        form.add(name);
        form.add(ownerName);
        form.add(address);
        form.add(manager);
        binder.bindInstanceFields(this);
        ownerName.setValue(farm.getOwner().getName());
        ownerName.setReadOnly(true);
        binder.readBean(this.farm);
        HorizontalLayout buttons = configureButtons();
        add(form, buttons);
    }


    private HorizontalLayout configureButtons()
    {
        save = new Button("Съхрани", l -> {
            if (binder.isValid())
            {
                try
                {
                    binder.writeBean(farm);
                    saved = true;
                }
                catch (ValidationException e)
                {
                    e.printStackTrace();
                }
            }
        });
        cancel = new Button("Откажи");
        final HorizontalLayout buttons = new HorizontalLayout(save, cancel);
        return buttons;
    }


    public void addCancelClickListener(ComponentEventListener<ClickEvent<Button>> clickListener)
    {
        cancel.addClickListener(clickListener);
    }


    public void addSaveClickListener(ComponentEventListener<ClickEvent<Button>> clickListener)
    {
        save.addClickListener(clickListener);
    }


    public boolean isSaved()
    {
        return saved;
    }
}

