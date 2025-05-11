function fileUpload(formId, urlPath, requiredHeaders) {
  const form = $(`#${formId}`);
  const fileInput = form.find('#fileInput')[0].files[0];

  if (!fileInput) {
    toastr.warning('Please select a file.');
    return;
  }
  requiredHeaders = requiredHeaders.map(h => h.toUpperCase().replace(/\s+/g, '_'));

  const fileType = fileInput.name.split('.').pop().toLowerCase();
  const reader = new FileReader();

  reader.onload = function (event) {
    let fileHeaders, jsonData;
    const data = new Uint8Array(event.target.result);

    if (fileType === 'csv') {
      const csvData = new TextDecoder().decode(data);
      const rows = csvData.split('\n').map(row => row.toUpperCase()); // Convert all rows to uppercase
      fileHeaders = rows[0].split(',').map(h => h.trim().replace(/\s+/g, '_')); // Normalize headers
      
      // Validate headers before processing
      const missingHeaders = requiredHeaders.filter(h => !fileHeaders.includes(h));
      if (missingHeaders.length) {
        toastr.warning(`Missing headers: ${missingHeaders.join(', ')}`);
        return;
      }

      jsonData = rows.slice(1).map(row => {
        const values = row.split(',');
        return fileHeaders.reduce((obj, header, index) => {
          obj[header] = values[index]?.trim() || null;
          return obj;
        }, {});
      });
    } else if (fileType === 'xls' || fileType === 'xlsx') {
      const workbook = XLSX.read(data, { type: 'array' });
      const sheet = workbook.Sheets[workbook.SheetNames[0]];
      const rawData = XLSX.utils.sheet_to_json(sheet, { header: 1 }).map(row =>
        row.map(cell => (cell ? cell.toString().toUpperCase() : null))
      ); // Convert all cells to uppercase

      fileHeaders = rawData[0].map(h => h.replace(/\s+/g, '_')); // Normalize headers
      
      // Validate headers before processing
      const missingHeaders = requiredHeaders.filter(h => !fileHeaders.includes(h));
      if (missingHeaders.length) {
        toastr.info(`Missing headers: ${missingHeaders.join(', ')}`);
        return;
      }

      jsonData = rawData.slice(1).map(row => {
        return fileHeaders.reduce((obj, header, index) => {
          obj[header] = row[index] || null;
          return obj;
        }, {});
      });
    } else {
      toastr.info('Unsupported file format.');
      return;
    }

    // Create FormData and send AJAX request
    const formData = new FormData();
    // formData.append('file', fileInput);
    formData.append('jsonData', JSON.stringify(jsonData));

    $.ajax({
      url: urlPath,
      type: 'POST',
      data: formData,
      processData: false,
      contentType: false,
      success: function () {
        toastr.success('File uploaded successfully.');
      },
      error: function () {
        toastr.danger('Error uploading file.');
      }
    });
  };

  reader.readAsArrayBuffer(fileInput);
}

function readFile(formId, requiredHeaders) {
  const form = $(`#${formId}`);
  const fileInput = form.find('#fileInput')[0].files[0];

  if (!fileInput) {
    toastr.warning('Please select a file.');
    return null;
  } 
  requiredHeaders = requiredHeaders.map(h => h.toUpperCase().replace(/\s+/g, '_'));
  const fileType = fileInput.name.split('.').pop().toLowerCase();
  const reader = new FileReader();

  return new Promise((resolve, reject) => {
    reader.onload = function (event) {
      let fileHeaders, jsonData;
      const data = new Uint8Array(event.target.result); 
      if (fileType === 'csv') {
        const csvData = new TextDecoder().decode(data);
        const rows = csvData.split('\n').map(row => row.toUpperCase()); // Convert all rows to uppercase
        fileHeaders = rows[0].split(',').map(h => h.trim().replace(/\s+/g, '_')); // Normalize headers

        const missingHeaders = requiredHeaders.filter(h => !fileHeaders.includes(h));
        if (missingHeaders.length) {
          toastr.warning(`Missing headers: ${missingHeaders.join(', ')}`);
          reject(`Missing headers: ${missingHeaders.join(', ')}`);
          return;
        }

        jsonData = rows.slice(1).map(row => {
          const values = row.split(',');
          return fileHeaders.reduce((obj, header, index) => {
            obj[header] = values[index]?.trim() || null;
            return obj;
          }, {});
        });

      } else if (fileType === 'xls' || fileType === 'xlsx') {
        const workbook = XLSX.read(data, { type: 'array' });
        const sheet = workbook.Sheets[workbook.SheetNames[0]];
        const rawData = XLSX.utils.sheet_to_json(sheet, { header: 1 }).map(row =>
          row.map(cell => (cell ? cell.toString().toUpperCase() : null))
        ); 

        fileHeaders = rawData[0].map(h => h.replace(/\s+/g, '_')); // Normalize headers

        const missingHeaders = requiredHeaders.filter(h => !fileHeaders.includes(h));
        if (missingHeaders.length) {
          toastr.info(`Missing headers: ${missingHeaders.join(', ')}`);
          reject(`Missing headers: ${missingHeaders.join(', ')}`);
          return;
        }

        jsonData = rawData.slice(1).map(row => {
          return fileHeaders.reduce((obj, header, index) => {
            obj[header] = row[index] || null;
            return obj;
          }, {});
        });

      } else {
        toastr.info('Unsupported file format.');
        reject('Unsupported file format.');
        return;
      }

      resolve(jsonData);
    };

    reader.onerror = function () {
      toastr.danger('Error reading file.');
      reject('Error reading file.');
    };

    reader.readAsArrayBuffer(fileInput);
  });
}

function uplaodFormDataToServer(formData,urlPath){
   // Create FormData and send AJAX request
  //  const formData = new FormData();
  //  formData.append('file', fileInput);
  //  formData.append('jsonData', JSON.stringify(jsonData));

   $.ajax({
     url: urlPath,
     type: 'POST',
     data: formData,
     processData: false,
     contentType: false,
     success: function () {
       toastr.success('Uploaded successfully.');
     },
     error: function () {
       toastr.danger('Error uploading.');
     }
   });
}