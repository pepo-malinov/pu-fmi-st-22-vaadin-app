package uni.pu.fmi.st.views.owner.components;


import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import uni.pu.fmi.st.data.entity.Owner;


public class OwnerForm extends VerticalLayout
{
    private TextField name = new TextField("Име");
    private TextField ownerId = new TextField("ЕГН/Булстат");
    private TextField email = new TextField("Email");
    private TextField gsm = new TextField("Тел.");
    private boolean saved;

    private Owner owner;
    private BeanValidationBinder<Owner> binder = new BeanValidationBinder<>(Owner.class);
    private Button cancel;
    private Button save;


    public OwnerForm(Owner owner)
    {
        this.owner = owner;
        init();
    }


    private void init()
    {
        setSizeFull();
        final FormLayout form = new FormLayout();
        form.add(name);
        form.add(ownerId);
        form.add(email);
        form.add(gsm);
        binder.bindInstanceFields(this);
        binder.readBean(this.owner);
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
                    binder.writeBean(owner);
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
