import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'roleNumberToString'
})
export class RoleNumberToStringPipe implements PipeTransform {

  transform(value: number): string {
    switch(value) {
      case 1: return "Admin";
      case 2: return "Advanced";
      case 3: return "Basic";
    }
    return null;
  }

}
