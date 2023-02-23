package view.coffee;

import commands.coffee.ChangeCoffeeCommand;
import main.Application;
import model.Coffee;
import model.db.Repository;
import view.AppGUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class ChangeCoffeeForm extends JFrame {
    public ChangeCoffeeForm(UUID id, Application app) {
        var data = Repository.selectCoffees(Repository.selectAllCoffeeSQL + " WHERE id = '" + id + "';");
        if (data != null) {
            setData(data);
            changeButt.addActionListener(l -> changeCoffee(id, app));
        } else {
            showErrorMessage("Cannot display coffee data!\nPlease, try again!");
        }
        initAggregateStateSpinner();
        initCoffeeStatusSpinner();
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
            aggregateState = (String) aggregateStateSpinner.getValue();
            volume = volumeTextField.getText();
            nettoWeight = nettoWeightTextField.getText();
            price = priceTextField.getText();
            qualityMark = qualityMarkTextField.getText();
            coffeeStatus = (String) coffeeStatusSpinner.getValue();
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

    private void setData(ArrayList<Coffee> coffee) {
        // TODO: Cannot set data for spinners :(
        Coffee temp = coffee.get(0);
        nameTextField.setText(temp.getName());
        volumeTextField.setText(String.valueOf(temp.getVolume()));
        nettoWeightTextField.setText(String.valueOf(temp.getNettoWeight()));
        priceTextField.setText(String.valueOf(temp.getPrice()));
        qualityMarkTextField.setText(String.valueOf(temp.getQualityMark()));
        transportationCompanyTextField.setText(temp.getTransportationCompany());
        cappingMarkTextField.setText(String.valueOf(temp.getQualityMark()));
        cultivationCountryTextField.setText(temp.getCultivationPlace().getCountry());
        cultivationStateTextField.setText(temp.getCultivationPlace().getState());
        cultivationFarmTextField.setText(temp.getCultivationPlace().getFarm());
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
    private JSpinner aggregateStateSpinner;
    private JTextField transportationCompanyTextField;
    private JTextField qualityMarkTextField;
    private JTextField priceTextField;
    private JTextField nettoWeightTextField;
    private JTextField volumeTextField;
    private JTextField cappingMarkTextField;
    private JTextField cultivationCountryTextField;
    private JTextField cultivationStateTextField;
    private JTextField cultivationFarmTextField;
    private JSpinner coffeeStatusSpinner;
    private JButton changeButt;
    private JLabel errorMessage;
    private JPanel changePanel;

    public Container getAddPanel() {
        return changePanel;
    }
}
