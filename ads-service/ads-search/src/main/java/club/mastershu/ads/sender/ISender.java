package club.mastershu.ads.sender;

import club.mastershu.ads.mysql.dto.MySQLRowData;

public interface ISender {
    void sender(MySQLRowData rowData);
}
