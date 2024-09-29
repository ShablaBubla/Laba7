package com.bubla;

import com.bubla.classes.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/** Класс опписывающий приложение
 *
 */
@Data
@AllArgsConstructor
public class Application implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean isRunnig = true;
    private Product newProd;

    public Application(){}

}
