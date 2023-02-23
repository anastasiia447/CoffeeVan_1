package view.coffee;

import commands.coffee.ChangeCoffeeCommand;
import main.Application;
import model.Coffee;
import model.db.Repository;
import model.enumerators.AggregateState;
import model.enumerators.CoffeeStatus;
import view.AppGUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class ChangeCoffeeForm extends JFrame {
    public ChangeCoffeeForm(UUID id, Application app) {
        initAggregateStateBox();
        initCoffeeStatusBox();

        var data = Repository.selectCoffees(Repository.selectAllCoffeeSQL + " WHERE id = '" + id + "';");
        if (data != null) {
            setData(data);
            changeButt.addActionListener(l -> changeCoffee(id, app));
        } else {
            showErrorMessage("Cannot display coffee data!\nPlease, try again!");
        }
    }

    private void changeCoffee(UUID id, Application app) {
        boolean flag = false;

        if (validate(nameTextField) && validate(volumeTextField) && validate(nettoWeightTextField) &&
                validate(priceTextField) && validate(qualityMarkTextField)) {
            flag = true;
        }


        if (flag) {
            String name, aggregateState, volume, nettoWeight, price, qualityMark,
                    coffeeStatus, transportationCompany, cappingMark, place;

            name = nameTextField.getText();
            aggregateState = aggregateStateBox.getSelectedItem().toString();
            volume = volumeTextField.getText();
            nettoWeight = nettoWeightTextField.getText();
            price = priceTextField.getText();
            qualityMark = qualityMarkTextField.getText();
            coffeeStatus = coffeeStatusBox.getSelectedItem().toString();
            transportationCompany = transportationCompanyTextField.getText();
            cappingMark = cappingMarkTextField.getText();
            place = cultivationCountryTextField.getText() + " " +
                    cultivationStateTextField.getText() + " " +
                    cultivationFarmTextField.getText();

            app.executeCommand(new ChangeCoffeeCommand(id, name, aggregateState, volume,
                    nettoWeight, price, qualityMark, coffeeStatus, place, transportationCompany,
                    cappingMark, app.getManager()));

            AppGUI.closeChangeCoffeeFrame();
        } else {
            showErrorMessage("Check input data!");
        }
    }

    private void initAggregateStateBox() {
        for (var state: AggregateState.values()) {
            aggregateStateBox.addItem(state);
        }
    }

    private void initCoffeeStatusBox() {
        for (var status: CoffeeStatus.values()) {
            coffeeStatusBox.addItem(status);
        }
    }

    private void setData(ArrayList<Coffee> coffee) {
        Coffee temp = coffee.get(0);
        nameTextField.setText(temp.getName());
        // volume
        volumeTextField.setText(String.valueOf(temp.getVolume()));
        // netto weight
        nettoWeightTextField.setText(String.valueOf(temp.getNettoWeight()));
        // price
        priceTextField.setText(String.valueOf(temp.getPrice()));
        // quality
        qualityMarkTextField.setText(String.valueOf(temp.getQualityMark()));
        // transport
        transportationCompanyTextField.setText(temp.getTransportationCompany());
        //capping
        cappingMarkTextField.setText(String.valueOf(temp.getQualityMark()));
        // cultivation
        cultivationCountryTextField.setText(temp.getCultivationPlace().getCountry());
        cultivationStateTextField.setText(temp.getCultivationPlace().getState());
        cultivationFarmTextField.setText(temp.getCultivationPlace().getFarm());
        // aggregate state
        aggregateStateBox.setSelectedItem(temp.getAggregateState());
        // coffee status
        coffeeStatusBox.setSelectedItem(temp.getCoffeeStatus());
    }

    private void showErrorMessage(String message) {
        errorMessage.setText(message);
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
        if (Objects.equals(text, "none")) {
            return false;
        } else return true;
    }

    private JTextField nameTextField;
    private JTextField transportationCompanyTextField;
    private JTextField qualityMarkTextField;
    private JTextField priceTextField;
    private JTextField nettoWeightTextField;
    private JTextField volumeTextField;
    private JTextField cappingMarkTextField;
    private JTextField cultivationCountryTextField;
    private JTextField cultivationStateTextField;
    private JTextField cultivationFarmTextField;
    private JButton changeButt;
    private JLabel errorMessage;
    private JPanel changePanel;
    private JComboBox<AggregateState> aggregateStateBox;
    private JComboBox<CoffeeStatus> coffeeStatusBox;

    public Container getAddPanel() {
        return changePanel;
    }
}
