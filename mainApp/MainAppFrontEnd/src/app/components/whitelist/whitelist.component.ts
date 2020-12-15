import { Component } from '@angular/core';
import { EthereumAccount } from 'src/app/models';
import { ErrorService } from 'src/app/services/error.service';
import { WhitelistService } from 'src/app/services/whitelist.service';

@Component({
  selector: 'app-whitelist',
  templateUrl: './whitelist.component.html',
  styles: [
  ]
})
export class WhitelistComponent {

  contract: string;

  ethereumAccount: EthereumAccount = {
    address: "",
    permission: true,
  };

  contracts: string[] = [
    "Acl", "Anomaly", "Client", "User"
  ];

  constructor(
    private service: WhitelistService,
    private errors: ErrorService,
  ) { }

  setPermission() {
    const response = this.ethereumAccount.permission ?
      confirm(`Adding ethereum account ${this.ethereumAccount.address} to smart contract '${this.contract}Store' whitelist. Is it corrrect?`) :
      confirm(`Removing ethereum account ${this.ethereumAccount.address} from smart contract '${this.contract}Store' whitelist. Is it corrrect?`);
    if(response === true) {
      this.service.setPermission(this.ethereumAccount, this.contract)
      .subscribe(data => {
        this.generateAlert();
      }, error => {
        this.errors.handleError(error)
      });
    }else{
      console.log("Operation cancelled");
    }
  }

  private generateAlert() {
    if(this.ethereumAccount.permission) {
      alert(`Ethereum account ${this.ethereumAccount.address} added to smart contract '${this.contract}Store' whitelist.`);
    } else {
      alert(`Ethereum account ${this.ethereumAccount.address} removed from smart contract '${this.contract}Store' whitelist.`);
    }
  }
}
