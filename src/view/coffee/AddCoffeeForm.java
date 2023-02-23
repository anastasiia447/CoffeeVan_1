package view.coffee;

import commands.coffee.AddCoffeeCommand;
import main.Application;
import view.AppGUI;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Objects;

public class AddCoffeeForm {
    private Application app;

    public AddCoffeeForm(Application app) {
        this.app = app;
        initAggregateStateSpinner();
        initCoffeeStatusSpinner();
        initAddButton(app);
    }

    private void initAddButton(Application app) {
        confirmButton.addActionListener(e -> {
            boolean flag = false;

            if (validate(nameField) && validate(volumeField) && validate(weightField) &&
                    validate(priceField) && validate(qualityField)) {
                flag = true;
            }

            if (flag) {
                String name, aggregateState, volume, nettoWeight, price, qualityMark,
                        coffeeStatus, transportationCompany, cappingMark, place;

                name = nameField.getText();
                aggregateState = (String) aggregateStateSpinner.getValue();
                volume = volumeField.getText();
                nettoWeight = weightField.getText();
                price = priceField.getText();
                qualityMark = qualityField.getText();
                coffeeStatus = (String) coffeeStatusSpinner.getValue();
                transportationCompany = transportationCompanyTextField.getText();
                cappingMark = cappingMarkTextField.getText();
                place = cultivationCountryTextField.getText() + " " +
                        cultivationStateTextField.getText() + " " +
                        cultivationFarmTextField.getText();

                app.executeCommand(new AddCoffeeCommand(name, aggregateState, volume, nettoWeight, price, qualityMark,
                        coffeeStatus, place, transportationCompany, cappingMark, app.getManager()));

                AppGUI.closeAddCoffeeFrame();
            } else {
                showErrorMessage();
            }
        });
    }

    private void initAggregateStateSpinner() {
        var list = new ArrayList<String>();
        list.add("cereal");
        list.add("ground");
        list.add("solubleInCans");
        list.add("solubleInSachets");

        aggregateStateSpinner.setModel(new SpinnerListModel(list));
    }

    private void initCoffeeStatusSpinner() {
        var list = new ArrayList<String>();
        list.add("auto");
        list.add("ordinary");
        list.add("higher");
        list.add("specialty");
        coffeeStatusSpinner.setModel(new SpinnerListModel(list));
    }

    private void  showErrorMessage() {
        errorMessage.setVisible(true);
    }

    /**
     * Check if there are data entered!
     *
     * @param field Field to check.
     * @return 'true' if data was entered and 'false' if not.
     */
    private boolean validate(JTextField field) {
        String text = field.getText();
        if (Objects.equals(text, "none")){
            return false;
        } else return true;
    }

    public JPanel getAddPanel() {
        return addPanel;
    }
    /* FIELDS */

    private JTextField cultivationFarmTextField;
    private JTextField cultivationStateTextField;
    private JTextField cultivationCountryTextField;
    private JTextField cappingMarkTextField;
    private JTextField transportationCompanyTextField;
    private JTextField qualityField;
    private JTextField priceField;
    private JTextField weightField;
    private JTextField volumeField;
    private JTextField nameField;
    private JSpinner aggregateStateSpinner;
    private JSpinner coffeeStatusSpinner;
    private JButton confirmButton;
    private JPanel addPanel;
    private JLabel errorMessage;
}
