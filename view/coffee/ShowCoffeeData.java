package view.coffee;

import model.Coffee;

import javax.swing.*;

public class ShowCoffeeData extends JDialog {
    public ShowCoffeeData(Coffee coffee) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        initData(coffee);
        buttonOK.addActionListener(e -> onOK());
    }

    private void initData(Coffee coffee) {
        idTextField.setText("Coffee {" + coffee.getId() + "}");

        nameTextField.setText(coffee.getName());
        aggregateTextField.setText(coffee.getAggregateState().name());
        volumeTextField.setText(coffee.getVolume() + " (cm^3)");
        weightTextField.setText(coffee.getNettoWeight() + " (gr)");
        priceTextField.setText(coffee.getPrice() + " (grn/100gr)");
        qualityTextField.setText(coffee.getQualityMark() + "");
        statusTextField.setText(coffee.getCoffeeStatus().name());
        transportationTextField.setText(coffee.getTransportationCompany());
        cappingTextField.setText(coffee.getCappingMark() + "");
        countryTextField.setText(coffee.getCultivationPlace().getCountry());
        stateTextField.setText(coffee.getCultivationPlace().getState());
        farmTextField.setText(coffee.getCultivationPlace().getFarm());
    }
    private void onOK() {
        dispose();
    }

    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel nameTextField;
    private JLabel aggregateTextField;
    private JLabel volumeTextField;
    private JLabel weightTextField;
    private JLabel priceTextField;
    private JLabel qualityTextField;
    private JLabel statusTextField;
    private JLabel transportationTextField;
    private JLabel cappingTextField;
    private JLabel countryTextField;
    private JLabel stateTextField;
    private JLabel farmTextField;
    private JLabel idTextField;

}
