package view;

import main.Settings;

import javax.swing.*;

import static helpers.Extension.parseFloat;

public class SettingsFrame extends JFrame{

    SettingsFrame() {
        ordinaryCoeffTextField.setText(String.valueOf(Settings.ordinaryQualityCoeff));
        higherCoeffTextField.setText(String.valueOf(Settings.higherQualityCoeff));
        specialtyCoeffTextField.setText(String.valueOf(Settings.specialtyQualityCoeff));
        logsFilePathTextField.setText(Settings.logFilePath);

        /* Buttons listeners*/
        saveButton.addActionListener(l -> onSavePressed());
        OKButton.addActionListener(l -> AppGUI.closeSettingFrame());
    }

    private void onSavePressed() {
        Settings.ordinaryQualityCoeff = parseFloat(ordinaryCoeffTextField.getText());
        Settings.higherQualityCoeff = parseFloat(higherCoeffTextField.getText());
        Settings.specialtyQualityCoeff = parseFloat(specialtyCoeffTextField.getText());
        logsFilePathTextField.setText(Settings.logFilePath);
        AppGUI.closeSettingFrame();
    }

    public JPanel getContentPlane() {
        return contentPlane;
    }

    private JPanel contentPlane;
    private JButton OKButton;
    private JButton saveButton;
    private JTextField ordinaryCoeffTextField;
    private JTextField higherCoeffTextField;
    private JTextField specialtyCoeffTextField;
    private JTextField logsFilePathTextField;
}
