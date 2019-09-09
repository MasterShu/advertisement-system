package club.mastershu.ads.sender;

import club.mastershu.ads.dto.MySQLRowData;

public interface ISender {
    void sender(MySQLRowData rowData);
}
