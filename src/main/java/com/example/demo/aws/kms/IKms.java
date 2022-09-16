package com.example.demo.aws.kms;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface IKms {

    Optional<String> encode(String key);
    Optional<Boolean> decode(String encValue);

}
