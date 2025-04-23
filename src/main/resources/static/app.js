function api(url, opts = {}) {
  const hasBody = opts.body !== undefined;

  
  const baseHeaders = hasBody
        ? { 'Content-Type': 'application/json' }
        : {};

  return fetch(url, {
           ...opts,
           headers: { ...baseHeaders, ...(opts.headers || {}) }
         })
    .then(async r => {
      if (!r.ok) {                      
        const txt = await r.text();
        throw new Error(txt || r.statusText);
      }

     
      const txt = await r.text();        
      return txt ? JSON.parse(txt) : null;
    });
}

function qs(sel)  { return document.querySelector(sel); }
function byId(id) { return document.getElementById(id); }

// automatically add admin headers if stored
function withAuth(opts = {}) {
  const u = sessionStorage.adminUser, p = sessionStorage.adminPwd;
  if (u && p)
    return { ...opts, headers:{ ...(opts.headers||{}), 'X-Username':u, 'X-Password':p } };
  return opts;
}

function togglePwd(btn, id){
  const field = document.getElementById(id);
  const show  = field.type === 'password';
  field.type  = show ? 'text' : 'password';
  btn.textContent = show ? 'Hide' : 'Show';
}